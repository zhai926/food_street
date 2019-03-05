package com.zhaihuilin.food.code.utils;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.AbstractUUIDGenerator;
import org.hibernate.id.Configurable;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 主键策略
 * Created by zhaihuilin on 2018/12/26 15:01.
 */
public class KeyGeneratorUtils extends AbstractUUIDGenerator implements Configurable{

  private  String k;

  @Override
  public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
      k = properties.getProperty("k");
  }

  @Override
  public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    return k + simpleDateFormat.format(new Date());
  }
}
