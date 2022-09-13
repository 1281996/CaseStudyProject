package com.cog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cog.dto.ResponseDto;
import com.cog.entity.Card;
import com.cog.service.CardService;

@RequestMapping("/card")
@CrossOrigin
@RestController
public class CardController {
	@Autowired
	CardService cardService;

	@PostMapping
	Card saveCardDetails(@RequestBody Card card) {
		return cardService.saveCardDetails(card);
	}

	@GetMapping("/{cardNumber}")
	Card getCardDetails(@PathVariable("cardNumber") Long cardNumber) {
		return cardService.getCardDetails(cardNumber);
	}

	@PutMapping("/{cardNumber}/{amount}")
	ResponseDto updateDebitedAmount(@PathVariable("cardNumber") Long cardNumber,
			@PathVariable("amount") Double amount) {
		return cardService.updateDebitedAmount(cardNumber, amount);
	}
}
