/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.logic.character;

/**
 *
 * @author Macauley
 * @param <E>
 */
public interface IterableEnum<E extends Enum<E>> {

  int ordinal();

  default E next() {
      E[] ies = this.getAllValues();
      return this.ordinal() != ies.length - 1 ? ies[this.ordinal() + 1] : null;
  }

  default E previous() {
      return this.ordinal() != 0 ? this.getAllValues()[this.ordinal() - 1] : null;
  }

  @SuppressWarnings("unchecked")
  public E[] getAllValues();
}