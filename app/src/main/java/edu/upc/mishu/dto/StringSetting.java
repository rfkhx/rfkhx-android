package edu.upc.mishu.dto;

import com.orm.SugarRecord;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StringSetting extends SugarRecord<StringSetting> implements Serializable {
    String item;
    String value;
}
