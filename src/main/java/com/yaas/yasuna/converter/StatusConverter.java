package com.yaas.yasuna.converter;

public class StatusConverter {

	public int convertStatusToInteger(String label) {

		switch(label) {

		case "未着手":

			return 0;

		case "着手中":

			return 1;

		case "もうすぐ終わる":

			return 2;

		case "課題あり":

			return 3;

		case "要救助":

			return 4;

		case "完了":

			return 9;

		default :

			return -1000;
		}
	}

}
