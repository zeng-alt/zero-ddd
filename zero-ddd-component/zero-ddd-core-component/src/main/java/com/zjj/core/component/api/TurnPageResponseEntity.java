package com.zjj.core.component.api;

import com.zjj.autoconfigure.component.core.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Collection;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月25日 21:41
 */
public class TurnPageResponseEntity<T, C> extends ResponseEntity<TurnPageEntity<Collection<T>, C>> {

    public TurnPageResponseEntity(HttpStatusCode status) {
        super(status);
    }

    public TurnPageResponseEntity(TurnPageEntity<Collection<T>, C> body, HttpStatusCode status) {
        super(body, status);
    }

    public static <T, C> TurnPageResponseEntity<T, C> of(boolean hasNext, boolean hasPre, C currentCursor, C nextCursor, Collection<T> data) {
        TurnPageEntity<Collection<T>, C> turnPageEntity = new TurnPageEntity<>();
        turnPageEntity.setHasNext(hasNext);
        turnPageEntity.setHasPre(hasPre);
        turnPageEntity.setCurrentCursor(currentCursor);
        turnPageEntity.setNextCursor(nextCursor);
        turnPageEntity.setData(data);
        return new TurnPageResponseEntity<>(turnPageEntity, HttpStatus.OK);

    }
}
