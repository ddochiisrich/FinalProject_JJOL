package com.example.project_jjol.model;

import java.sql.Timestamp;
import java.util.List;

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
public class AllCommunity {
	private int allcNo;
	private String allcName;
	private String allcTitle;
	private String allcContent;
    private Timestamp allcDate;
    private String allcPass;
    private String allcFile;


}


