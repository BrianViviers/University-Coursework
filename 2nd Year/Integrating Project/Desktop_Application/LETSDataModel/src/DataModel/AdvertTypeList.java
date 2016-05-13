/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

import java.util.ArrayList;

/**
 * This class represents a list of advert types.
 *
 * @author Richard
 */
public class AdvertTypeList {

    private ArrayList<AdvertType> advertList;

    /**
     * Default constructor initialising variables.
     */
    public AdvertTypeList() {
        this.advertList = new ArrayList<>();
    }

    /**
     * Method to add an advert type to the list of advert types.
     *
     * @param newAdvertType - Advert Type to add to the list.
     */
    public void addAdvertType(AdvertType newAdvertType) {
        if (null != newAdvertType) {
            if (null == advertList) {
                advertList = new ArrayList<>();
            }
            advertList.add(newAdvertType);
        }

    }

    /**
     * Method to get a specific advert type based on it's index.
     *
     * @param index - int value being the index in which the advert type is
     * stored.
     * @return - Advert Type found at the specified index.
     */
    public AdvertType getAdvertTypeAt(int index) {
        AdvertType adType = null;
        if (null != advertList) {
            adType = advertList.get(index);

        }
        return adType;
    }

    /**
     * Clears the advert type list.
     */
    public void clearAdvertTypeList() {
        if (this.advertList.size() >= 0) {
            this.advertList.clear();
        }
    }
    
    /**
     * Returns the size of the advert type list.
     * @return - int value being the size of the advert list.
     */
    public int allAdvertTypes()
    {
        return advertList.size();
    }
}
