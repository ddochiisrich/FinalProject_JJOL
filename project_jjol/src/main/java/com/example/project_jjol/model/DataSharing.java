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
public class DataSharing {
	
	private String dataName;
	private String dataTitle;
	private String dataContent;
    private Timestamp dataDate;
    private String dataPw;
    private String dataFile;

}
