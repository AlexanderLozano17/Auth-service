package com.authservice.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuleDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String nameEs;
	private String nameEn;
	private String descriptionEs;
	private String descriptionEn;
	private Boolean active;
}
