package com.ktcraft.pvs.service.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @DynamoDBAttribute(attributeName = "organization_id")
    public Long organizationId;

    @DynamoDBAttribute(attributeName = "account_id")
    public Long accountId;

    @DynamoDBAttribute(attributeName = "product_id")
    public Integer productId;
}
