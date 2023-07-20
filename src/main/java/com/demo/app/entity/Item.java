package com.demo.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;


@Entity
@Table(name = "item")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    @JsonIgnore
    private PermissionGroup group;
    
    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "parent_id", nullable = true)
    @JsonIgnore
    private Item parentId;

    
}
