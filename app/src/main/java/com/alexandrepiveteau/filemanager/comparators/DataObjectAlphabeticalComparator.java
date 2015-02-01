package com.alexandrepiveteau.filemanager.comparators;

import com.alexandrepiveteau.filemanager.files.DataObject;

import java.util.Comparator;

/**
 * Created by Alexandre on 1/25/2015.
 */
public class DataObjectAlphabeticalComparator implements Comparator<DataObject> {
    @Override
    public int compare(DataObject dataObject, DataObject dataObject2) {
        int res = String.CASE_INSENSITIVE_ORDER.compare(dataObject.getName(), dataObject2.getName());
        if (res == 0) {
            res = dataObject.getName().compareTo(dataObject2.getName());
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
