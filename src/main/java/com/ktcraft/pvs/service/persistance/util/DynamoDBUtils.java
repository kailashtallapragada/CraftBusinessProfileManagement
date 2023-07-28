package com.ktcraft.pvs.service.persistance.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.ktcraft.pvs.constants.AppConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Service
public class DynamoDBUtils {

    @Cacheable(cacheNames = AppConstants.LOCAL_CACHE_KEY)
    public DynamoDBSaveExpression saveWithoutPrimaryKey(Class c) {
        final DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        final Map expected = new HashMap();
        final Field[] fields = c.getDeclaredFields();
        for (final Field field : fields) {
            final DynamoDBHashKey[] hashKeys = field.getAnnotationsByType(DynamoDBHashKey.class);
            for (DynamoDBHashKey hashKey: hashKeys) {
                expected.put(hashKey.attributeName(), new ExpectedAttributeValue().withExists(false));
            }
        }
        dynamoDBSaveExpression.setExpected(expected);
        return dynamoDBSaveExpression;
    }
}
