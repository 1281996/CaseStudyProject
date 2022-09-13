package com.cog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cog.dto.ResponseDto;
import com.cog.entity.Card;
import com.cog.service.CardService;
import com.cog.util.TestInputs;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {
	@Mock
	CardService cardService;
	@InjectMocks
	CardController cardController;

	@Test
	void testSaveCardDetails() {
		Card card = TestInputs.getCard();
		when(cardService.saveCardDetails(card)).thenReturn(card);
		assertEquals(1, cardController.saveCardDetails(card).getId());
	}

	@Test
	void testGetCardDetails() {
		Card card = TestInputs.getCard();
		when(cardService.getCardDetails(12345L)).thenReturn(card);
		assertEquals(card.getId(), cardController.getCardDetails(12345L).getId());
	}

	@Test
	void testUpdateDebitedAmount() {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setResponse("");
		when(cardService.updateDebitedAmount(12345L, 234.0)).thenReturn(responseDto);
		assertEquals("", cardController.updateDebitedAmount(12345L, 234.0).getResponse());
	}

	

}
