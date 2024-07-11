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
public class DataSharingComment {

	private int dscNo;
	private int dscId;
	//부모 테이블 datasharing의 data_no를 참조하는 외래키
	private int ddNo;
	private String dscContent;
	private String dscWriter;
	private Timestamp dscTime;
}
