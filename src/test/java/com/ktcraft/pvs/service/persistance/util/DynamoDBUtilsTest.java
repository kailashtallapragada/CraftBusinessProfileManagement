package com.ktcraft.pvs.service.persistance.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DynamoDBUtilsTest {

    @Autowired
    DynamoDBUtils dynamoDBUtils;

    @Test
    void saveWithoutPrimaryKey() {
        final DynamoDBSaveExpression dynamoDBSaveExpression = dynamoDBUtils.saveWithoutPrimaryKey(TestTable.class);
        final Map<String, ExpectedAttributeValue> expected = dynamoDBSaveExpression.getExpected();
        assertEquals(1, expected.size());
    }
}