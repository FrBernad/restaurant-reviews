package com.peya.interview.persistance.generator;

import com.github.f4b6a3.uuid.UuidCreator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

import java.util.EnumSet;

public class UuidV7Generator implements BeforeExecutionGenerator {

  @Override
  public Object generate(
      final SharedSessionContractImplementor sharedSessionContractImplementor,
      final Object owner,
      final Object currentValue,
      final EventType eventType) {
    if (currentValue != null) {
      return currentValue;
    }
    return UuidCreator.getTimeOrderedEpoch();
  }

  @Override
  public EnumSet<EventType> getEventTypes() {
    return EnumSet.of(EventType.INSERT);
  }
}
