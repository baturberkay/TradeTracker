package com.interview.tradetracker.util;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class DtoEntityMapper {
    private static ModelMapper instance;
    synchronized public static ModelMapper getInstance() {
      if (instance == null){
        instance = new org.modelmapper.ModelMapper();
        instance.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //configuration for updating only given keys. it will skip null keys.
        instance.getConfiguration().setPropertyCondition(Conditions.isNotNull());
      }
      return instance;
    }
}
