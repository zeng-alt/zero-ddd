package com.zjj.security.rbac.component.router;

import org.springframework.web.util.pattern.PathPatternParser;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月07日 21:42
 */
public class RouteTemplateTrie {
    private final TrieNode root = new TrieNode();
    private PathPatternParser pathPatternParser;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    static class TrieNode {
        Map<String, TrieNode> children = new HashMap<>();
        String fullTemplate = null;
        boolean isVariable = false;
    }

    private String normalizePart(String part) {
        return (part.startsWith("{") && part.endsWith("}")) ? "{}" : part;
    }

    public void insert(String template) {
        lock.writeLock().lock();
        try {
            String[] parts = template.split("/");
            TrieNode node = root;
            for (String part : parts) {
                if (part.isEmpty()) continue;

                String key = normalizePart(part);
                node.children.putIfAbsent(key, new TrieNode());
                node = node.children.get(key);
                node.isVariable = "{}".equals(key);
            }
            node.fullTemplate = template;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String match(String actualPath) {
        lock.readLock().lock();
        try {
            String[] parts = this.tokenizePath(actualPath);
            return matchRecursive(parts, 0, root);
        } finally {
            lock.readLock().unlock();
        }
    }

    private String[] tokenizePath(String path) {
        return Arrays.stream(path.split("/"))
                .filter(part -> !part.isEmpty())
                .toArray(String[]::new);
    }

    private String matchRecursive(String[] parts, int index, TrieNode node) {
        if (index == parts.length) {
            return node.fullTemplate;
        }

        String part = parts[index];

        // 优先静态路径
        TrieNode exactNode = node.children.get(part);
        if (exactNode != null) {
            String result = matchRecursive(parts, index + 1, exactNode);
            if (result != null) return result;
        }

        // 再尝试变量路径段（{}）
        TrieNode variableNode = node.children.get("{}");
        if (variableNode != null) {
            String result = matchRecursive(parts, index + 1, variableNode);
            if (result != null) return result;
        }

        return null;
    }

    public List<String> getAllTemplates() {
        List<String> results = new ArrayList<>();
        lock.readLock().lock();
        try {
            collectTemplates(root, results);
        } finally {
            lock.readLock().unlock();
        }
        return results;
    }

    private void collectTemplates(TrieNode node, List<String> results) {
        if (node.fullTemplate != null) {
            results.add(node.fullTemplate);
        }
        for (TrieNode child : node.children.values()) {
            collectTemplates(child, results);
        }
    }

    /**
     * 删除以某个路径为前缀的所有模板路径
     */
    public void deleteSubtree(String prefixPath) {
        lock.writeLock().lock();
        try {
            String[] parts = tokenizePath(prefixPath);
            deleteRecursive(root, parts, 0);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private boolean deleteRecursive(TrieNode node, String[] parts, int index) {
        if (index == parts.length) {
            // 清空当前子树
            node.children.clear();
            node.fullTemplate = null;
            return true;
        }

        String key = normalizePart(parts[index]);
        TrieNode child = node.children.get(key);
        if (child != null) {
            boolean deleted = deleteRecursive(child, parts, index + 1);
            if (deleted) {
                if (child.children.isEmpty() && child.fullTemplate == null) {
                    node.children.remove(key);
                }
            }
        }
        return false;
    }
}
