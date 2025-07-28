package com.zjj.camunda.infrastructure.query;

import org.camunda.bpm.engine.exception.NotValidException;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.UserQuery;
import org.camunda.bpm.engine.impl.Direction;
import org.camunda.bpm.engine.impl.QueryOrderingProperty;
import org.camunda.bpm.engine.impl.UserQueryProperty;
import org.camunda.bpm.engine.query.QueryProperty;

import java.util.ArrayList;
import java.util.List;

import static org.camunda.bpm.engine.impl.util.EnsureUtil.ensureNotNull;
import static org.camunda.bpm.engine.impl.util.EnsureUtil.ensureNull;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年07月28日 21:09
 */
public class ZeroUserQueryImpl implements UserQuery {

    private static final long serialVersionUID = 1L;
    protected String id;
    protected String[] ids;
    protected String firstName;
    protected String firstNameLike;
    protected String lastName;
    protected String lastNameLike;
    protected String email;
    protected String emailLike;
    protected String groupId;
    protected String procDefId;
    protected String tenantId;
    protected List<QueryOrderingProperty> orderingProperties = new ArrayList<QueryOrderingProperty>();


    public UserQuery userId(String id) {
        ensureNotNull("Provided id", id);
        this.id = id;
        return this;
    }

    public UserQuery userIdIn(String... ids) {
        ensureNotNull("Provided ids", ids);
        this.ids = ids;
        return this;
    }

    public UserQuery userFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserQuery userFirstNameLike(String firstNameLike) {
        ensureNotNull("Provided firstNameLike", firstNameLike);
        this.firstNameLike = firstNameLike;
        return this;
    }

    public UserQuery userLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserQuery userLastNameLike(String lastNameLike) {
        ensureNotNull("Provided lastNameLike", lastNameLike);
        this.lastNameLike = lastNameLike;
        return this;
    }

    public UserQuery userEmail(String email) {
        this.email = email;
        return this;
    }

    public UserQuery userEmailLike(String emailLike) {
        ensureNotNull("Provided emailLike", emailLike);
        this.emailLike = emailLike;
        return this;
    }

    public UserQuery memberOfGroup(String groupId) {
        ensureNotNull("Provided groupId", groupId);
        this.groupId = groupId;
        return this;
    }

    public UserQuery potentialStarter(String procDefId) {
        ensureNotNull("Provided processDefinitionId", procDefId);
        this.procDefId = procDefId;
        return this;

    }

    public UserQuery memberOfTenant(String tenantId) {
        ensureNotNull("Provided tenantId", tenantId);
        this.tenantId = tenantId;
        return this;
    }

    //sorting //////////////////////////////////////////////////////////

    public UserQuery orderByUserId() {
        return orderBy(UserQueryProperty.USER_ID);
    }

    public UserQuery orderByUserEmail() {
        return orderBy(UserQueryProperty.EMAIL);
    }

    public UserQuery orderByUserFirstName() {
        return orderBy(UserQueryProperty.FIRST_NAME);
    }

    public UserQuery orderByUserLastName() {
        return orderBy(UserQueryProperty.LAST_NAME);
    }

    public UserQuery orderBy(QueryProperty property) {
        return orderBy(new QueryOrderingProperty(null, property));
    }

    @SuppressWarnings("unchecked")
    public UserQuery orderBy(QueryOrderingProperty orderProperty) {
        this.orderingProperties.add(orderProperty);
        return this;
    }

    //getters //////////////////////////////////////////////////////////

    public String getId() {
        return id;
    }
    public String[] getIds() {
        return ids;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getFirstNameLike() {
        return firstNameLike;
    }
    public String getLastName() {
        return lastName;
    }
    public String getLastNameLike() {
        return lastNameLike;
    }
    public String getEmail() {
        return email;
    }
    public String getEmailLike() {
        return emailLike;
    }
    public String getGroupId() {
        return groupId;
    }
    public String getTenantId() {
        return tenantId;
    }

    public UserQuery asc() {
        return direction(Direction.ASCENDING);
    }

    public UserQuery desc() {
        return direction(Direction.DESCENDING);
    }

    /**
     * Executes the query and returns the number of results
     */
    @Override
    public long count() {
        return 0;
    }

    @SuppressWarnings("unchecked")
    public UserQuery direction(Direction direction) {
        QueryOrderingProperty currentOrderingProperty = null;

        if (!orderingProperties.isEmpty()) {
            currentOrderingProperty = orderingProperties.get(orderingProperties.size() - 1);
        }

        ensureNotNull(NotValidException.class, "You should call any of the orderBy methods first before specifying a direction", "currentOrderingProperty", currentOrderingProperty);

        if (currentOrderingProperty.getDirection() != null) {
            ensureNull(NotValidException.class, "Invalid query: can specify only one direction desc() or asc() for an ordering constraint", "direction", direction);
        }

        currentOrderingProperty.setDirection(direction);
        return this;
    }


    @Override
    public User singleResult() {
        return null;
    }


    @Override
    public List<User> list() {
        return null;
    }


    @Override
    public List<User> unlimitedList() {
        return null;
    }


    @Override
    public List<User> listPage(int firstResult, int maxResults) {
        return null;
    }
}
