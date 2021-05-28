package com.adobe.aem.guides.wknd.core.models;

import java.util.List;

public interface BylineModel {
    /***
     * @return a string a display as the name.
     */
    String getName();

    /***
     * Occupations are to be sorted alphabetically in a descending order.
     *
     * @return a list of occupations.
     */
    List<String> getOccupations();

    /***
     * @return a boolean if the component has enough content to display.
     */
    boolean isEmpty();
}