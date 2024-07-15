package com.example.project_jjol.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityComment {

	private int cmcNo;
	private int ccNo;
	private String cmcContent;
	private String cmcWriter;
	private Timestamp cmcTime;
}
