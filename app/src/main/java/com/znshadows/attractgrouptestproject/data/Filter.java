package com.znshadows.attractgrouptestproject.data;

import java.util.ArrayList;

/**
 * Created by kostya on 10.05.2016.
 */
public class Filter {
    private String filterText;
    private ArrayList<SuperHero> filteredHeroes = new ArrayList<SuperHero>();

    /**
     * Preparation of the filtered Array from the full array of data
     *
     * @param filterText
     * @param heroesToFilter
     */
    public Filter(String filterText, ArrayList<SuperHero> heroesToFilter) {
        this.filterText = filterText;

        if (heroesToFilter != null) {
            if (filterText.equals("")) {

                this.filteredHeroes = heroesToFilter;
            } else {
                for (int i = 0; i < heroesToFilter.size(); i++) {

                    if (isItemFiltered(filterText, heroesToFilter, i)) {
                        filteredHeroes.add(heroesToFilter.get(i));
                    }
                }//end for
            }//end else
        }

    }

    /**
     *
     * @return Array of items that matches preseted flter
     */
    public ArrayList<SuperHero> getFilteredHeroes() {
        return filteredHeroes;
    }

    /**
     * is this item, matches filter
     *
     * @param filterText string to match item name
     * @param position   position of item in list
     * @return true if there is a match
     */
    private boolean isItemFiltered(String filterText, ArrayList<SuperHero> heroesToFilter, int position) {
        if (heroesToFilter.get(position).getName().toLowerCase().contains(filterText.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }
}
