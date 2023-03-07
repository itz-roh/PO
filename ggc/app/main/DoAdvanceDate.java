package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.exception.InvalidDateException;
import ggc.core.exception.BadEntryException;

/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {

  DoAdvanceDate(WarehouseManager receiver){
    super(Label.ADVANCE_DATE, receiver);
    addIntegerField("number", Message.requestDaysToAdvance());
  }

  @Override
  public final void execute() throws CommandException ,InvalidDateException {
    try{
    _receiver.getDate().addToNow(integerField("number"));
    } catch (BadEntryException bee){
      throw new InvalidDateException(integerField("number"));
    }
  }

}
