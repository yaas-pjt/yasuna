package com.yaas.yasuna.converter;

public interface ConverterLogic <E,F> {

	F convertEntityToForm(E e);

	E convertFormToEntity(F f);
}
