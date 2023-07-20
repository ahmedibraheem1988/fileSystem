package com.demo.app.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.app.entity.File;
import com.demo.app.entity.Item;
import com.demo.app.entity.PermissionGroup;
import com.demo.app.entity.Permissions;
import com.demo.app.model.CreateItemRequest;
import com.demo.app.repository.FileRepository;
import com.demo.app.repository.ItemRepository;
import com.demo.app.repository.PermissionGroupRepository;
import com.demo.app.repository.PermissionsRepository;

@RestController
@RequestMapping("/api")
public class AnswerController {

	@Autowired
	PermissionGroupRepository pgrepo;

	@Autowired
	PermissionsRepository prepo;

	@Autowired
	ItemRepository irepo;

	@Autowired
	FileRepository frepo;

	@PostMapping("/createSpace")
	public ResponseEntity<Item> createSpace(@RequestBody CreateItemRequest request) throws Exception {
		Item item = new Item();
		item.setName(request.getName());
		item.setType(request.getType());
		PermissionGroup group = pgrepo.findById(request.getGropId()).orElseThrow(() -> new Exception("id not found"));
		item.setGroup(group);
		irepo.save(item);
		return new ResponseEntity<>(item, HttpStatus.CREATED);

	}

	@PostMapping("/createFolder")
	public ResponseEntity<?> createFolder(@RequestBody CreateItemRequest request) throws Exception {

		PermissionGroup group = pgrepo.findById(request.getGropId())
				.orElseThrow(() -> new Exception("group id not found"));
		List<Permissions> p = prepo.getP(request.getUserEmail(), group);
		if (p == null || p.isEmpty() || !"EDIT".equals(p.get(0).getLevel())) {
			return new ResponseEntity<>("you don not have access", HttpStatus.UNAUTHORIZED);
		}
		Item item = new Item();
		item.setName(request.getName());
		item.setType(request.getType());
		Item pItem = irepo.findById(request.getParentId()).orElseThrow(() -> new Exception("item id not found"));
		item.setParentId(pItem);
		item.setGroup(group);
		irepo.save(item);
		return new ResponseEntity<>(item, HttpStatus.CREATED);

	}

	@PostMapping("/createFile")
	public ResponseEntity<?> createFile(@RequestParam("file") MultipartFile file,
			@RequestParam("parentId") Long parentId, @RequestParam("email") String email,
			@RequestParam("name") String name, @RequestParam("type") String type) throws Exception {

		Item parent = irepo.findById(parentId).orElseThrow(() -> new Exception("group id not found"));
		List<Permissions> p = prepo.getP(email, parent.getGroup());
		if (p == null || p.isEmpty() || !"EDIT".equals(p.get(0).getLevel())) {
			return new ResponseEntity<>("you don not have access", HttpStatus.UNAUTHORIZED);
		}
		Item item = new Item();
		item.setName(name);
		item.setType(type);
		item.setParentId(parent);
		item.setGroup(parent.getGroup());
		irepo.save(item);

		File filebinary = new File();
		filebinary.setData(file.getBytes());
		filebinary.setItem(item);
		frepo.save(filebinary);

		return new ResponseEntity<>(item, HttpStatus.CREATED);

	}
	
	
	@GetMapping("/filemetadata")
	public ResponseEntity<?> getfiledata(@RequestParam("id") Long id,@RequestParam("email") String email) throws Exception {

		Item item = irepo.findById(id).orElseThrow(() -> new Exception("item id not found"));
		
		List<Permissions> p = prepo.getP(email, item.getGroup());
		if (p == null || p.isEmpty() || !"EDIT".equals(p.get(0).getLevel())) {
			return new ResponseEntity<>("you don not have access", HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(item, HttpStatus.OK);

	}
	
	@GetMapping("/file")
	public ResponseEntity<?> getfile(@RequestParam("id") Long id,@RequestParam("email") String email) throws Exception {

		Item item = irepo.findById(id).orElseThrow(() -> new Exception("item id not found"));
		
		List<Permissions> p = prepo.getP(email, item.getGroup());
		if (p == null || p.isEmpty() || !"EDIT".equals(p.get(0).getLevel())) {
			return new ResponseEntity<>("you don not have access", HttpStatus.UNAUTHORIZED);
		}
		
		File b= frepo.findByItem(item);
		
		return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + item.getName() + "\"")
                .body(b.getData());

	}

	
	
}