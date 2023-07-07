package ru.insyres.PERCo_Web_11_81.dto.wrapped;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WrapperOne<T> {
	private T content;
	private boolean success;
	private String message;
	private String snackbarType;
	@SuppressWarnings("rawtypes")
	public WrapperOne forLogger() {
		return WrapperOne.builder()
				.content(null)
				.success(this.success)
				.message(this.message)
				.snackbarType(this.snackbarType)
				.build();
	}
	public WrapperOne() {}
}
