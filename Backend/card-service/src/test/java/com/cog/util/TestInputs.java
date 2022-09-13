package com.cog.util;

import com.cog.entity.Card;

public class TestInputs {
	public static Card getCard() {
		Card card = new Card();
		card.setAmount(1234.0);
		card.setCardNumber(12345L);
		card.setCvc(235L);
		card.setEmail("kamma@gmail.com");
		card.setId(1);
		return card;
	}
}
