package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.midi.Receiver;
import ggc.core.exception.MissingFileAssociationException;
import ggc.core.WarehouseManager;
import ggc.core.Warehouse;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

  /** @param receiver */
  DoSaveFile(WarehouseManager receiver){
    super(Label.SAVE, receiver); 
  }

  @Override
  public final void execute() throws CommandException {
    try {
      if(_receiver.getFileName().equals("")){
        Form txt = new Form();
        _receiver.saveAs(txt.requestString(Message.newSaveAs()));
      }
      else{
        _receiver.save();
      }
    } catch (IOException | MissingFileAssociationException e) {
    }
  }
}
