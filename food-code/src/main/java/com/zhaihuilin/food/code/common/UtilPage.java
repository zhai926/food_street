package com.zhaihuilin.food.code.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Sort;

/**
 * Created by zhaihuilin on 2019/1/4 13:25.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UtilPage {

  /**
   * 页数
   */
  private int pageNum;

  /**
   * 每页显示数量
   */
  private int pageSize;

  /**
   * 排序字段
   */
  private String pageSort;

  /**
   * 排序方向
   */
  private Sort.Direction direction;

}
