package pt.ipbeja.estig.anim.gui;

import pt.ipbeja.estig.anim.model.Move;

/**
 * The fifteen puzzle view
 *
 * @author João Paulo Barros
 * @version 2021/05/18
 */
public interface View {
    void notifyView(Move move, Boolean winning, int tValue);
}
