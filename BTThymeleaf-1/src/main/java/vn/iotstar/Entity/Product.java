package vn.iotstar.Entity;

import java.io.Serializable;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	@Column(length = 500, columnDefinition = "nvarchar(500) not null")
	private String productName;
	@Column(nullable = false)
	private int quantity;
	@Column(nullable = false)
	private double unitPrice;
	@Column(length = 200)
	private String images;
	@Column(columnDefinition = "nvarchar(500) not null")
	private String description;
	@Column(nullable = false)
	private double discount;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "YYYY-MM-DD hh:mi:ss")
	private Date createDate;
	@Column(nullable = false)
	private short status;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="categoryID")
	private Category category;
	@Transient
    private MultipartFile imageFile;
	public Object getCategoryID() {
		// TODO Auto-generated method stub
		return category.getCategoryID();
	}
	public MultipartFile getImages() {
		// TODO Auto-generated method stub
		return this.imageFile;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public MultipartFile getImageFile() {
		return imageFile;
	}
	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setImages(String images) {
		this.images = images;
	}
	

}
