package com.cog.dto;

import java.io.Serializable;
import java.util.List;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyDto implements Serializable {

	private static final long serialVersionUID = 4205654421606975861L;
	
	private String email;
	
	private String name;
	
	private Integer bookId;
	
	private Long cardNumber;
	
	private Long cvc;
	
	private List<BookDto> books;
	
	private Double amount;
}
