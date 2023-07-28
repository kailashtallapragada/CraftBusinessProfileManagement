package com.ktcraft.pvs.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;

public class Address {

    @DynamoDBAttribute(attributeName = "address_line_1")
    public String addressLine1;

    @DynamoDBAttribute(attributeName = "address_line_2")
    public String addressLine2;

    @DynamoDBAttribute(attributeName = "city")
    public String city;

    @DynamoDBAttribute(attributeName = "zip")
    public String zip;

    @DynamoDBAttribute(attributeName = "country")
    public String country;
}
