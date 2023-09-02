package com.security.session.controller.responseMsg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomMessage {

  private EnumStatus status;
  private String message;
  private Object data;

}
