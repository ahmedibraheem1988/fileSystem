package com.demo.app.model;

import lombok.Data;

@Data
public class CreateItemRequest {
 String name;
 String type;
 long parentId;
 long gropId;
 String userEmail;
	
}
