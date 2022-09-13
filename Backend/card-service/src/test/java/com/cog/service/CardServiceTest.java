package com.cog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cog.entity.Card;
import com.cog.repostiory.CardRepository;
import com.cog.util.TestInputs;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
	@InjectMocks
	CardService cardService;
	@Mock
	CardRepository cardRepository;

	@Test
	void testSaveCardDetails() {
		Card card = TestInputs.getCard();
		when(cardRepository.findByCardNumber(card.getCardNumber())).thenReturn(card);
		assertEquals(card.getId(), cardService.saveCardDetails(card).getId());
	}
	@Test
	void testSaveCardDetailsWhenCardNotExistinDb() {
		Card card = TestInputs.getCard();
		when(cardRepository.findByCardNumber(card.getCardNumber())).thenReturn(null);
		assertEquals(card.getId(), cardService.saveCardDetails(card).getId());
	}

	@Test
	void testGetCardDetails() {
		when(cardRepository.findByCardNumber(12345L)).thenReturn(TestInputs.getCard());
		assertEquals(1, cardService.getCardDetails(12345L).getId());
	}

	@Test
	void testUpdateDebitedAmount() {
		when(cardRepository.findByCardNumber(12345L)).thenReturn(TestInputs.getCard());
		assertEquals("Amount debited sucessfully", cardService.updateDebitedAmount(12345L, 123.0).getResponse());
	}
	@Test
	void testUpdateDebitedAmountWhenFaiure() {
		when(cardRepository.findByCardNumber(12345L)).thenReturn(null);
		assertEquals("Failure", cardService.updateDebitedAmount(12345L, 123.0).getResponse());
	}

}
