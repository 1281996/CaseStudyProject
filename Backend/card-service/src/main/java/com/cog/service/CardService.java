package com.cog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cog.dto.ResponseDto;
import com.cog.entity.Card;
import com.cog.repostiory.CardRepository;

@Service
public class CardService {
	@Autowired
	CardRepository cardRepository;

	public Card saveCardDetails(Card card) {
		card.setAmount(500000.0);
		Card cardRes = cardRepository.findByCardNumber(card.getCardNumber());
		if (cardRes == null)
			cardRepository.save(card);
		return card;
	}

	public Card getCardDetails(Long cardNumber) {

		return cardRepository.findByCardNumber(cardNumber);
	}

	public ResponseDto updateDebitedAmount(Long cardNumber, Double amount) {
		Card cardRes = cardRepository.findByCardNumber(cardNumber);
		ResponseDto responseDto = new ResponseDto();
		if (cardRes != null) {
			cardRes.setAmount(cardRes.getAmount() - amount);
			responseDto.setResponse("Amount debited sucessfully");
		} else {
			responseDto.setResponse("Failure");
		}
		return responseDto;
	}

}
