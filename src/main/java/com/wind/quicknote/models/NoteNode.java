package com.wind.quicknote.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * Id, Name, IconUrl, Status, ParentId, OwnerId, OwnerName, Content, Created, Updated
 * 
 * http://viralpatel.net/blogs/hibernate-one-to-one-mapping-tutorial-using-annotation/
 * http://javadigest.wordpress.com/2012/01/27/using-the-sequence-generator-in-hibernate/
 * http://www.mkyong.com/hibernate/hibernate-fetching-strategies-examples/
 * 
 */

@NamedQueries
({
	
		@NamedQuery(name = "findSimpleNodeById", query = "from NoteNode where id = :Id"),

		@NamedQuery(name = "findSimpleNodeById2", query = "from NoteNode where id = :Id") 

})

@Entity
@Table(name = "notenodes")
public class NoteNode {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "NodeSequence", sequenceName = "node_seq", allocationSize=5)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NodeSequence")
	private long id;

	@ManyToOne
	@JoinColumn(name="parentId")
	private NoteNode parent;
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "parent", fetch = FetchType.LAZY)
	//@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "parent", fetch = FetchType.EAGER)
	//@Fetch(FetchMode.JOIN)
	private List<NoteNode> children = new ArrayList<NoteNode>();
	
	@Column(name="name")
	private String name;
	
	@Column(name="ownerId")
	private String ownerId;
	@Column(name="tag")
	private String tag;
	@Column(name="text", length=4000)
	private String text;
	@Column(name="attachment")
	private byte[] attachment;
	@Column(name="iconurl")
	private String icon;
	@Column(name="status")
	private String status;
	
	@Column(name="created")
	private Date created;
	@Column(name="updated")
	private Date updated;
	
	public NoteNode() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public NoteNode getParent() {
		return parent;
	}

	public void setParent(NoteNode parent) {
		this.parent = parent;
	}

	public List<NoteNode> getChildren() {
		return children;
	}

	public void setChildren(List<NoteNode> children) {
		this.children = children;
	}
	
	public boolean hasChildren() {
		
		if (children != null && children.size() > 0)
			return true;
		
		return false;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
}
