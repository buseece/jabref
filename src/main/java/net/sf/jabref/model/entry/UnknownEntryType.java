/*  Copyright (C) 2003-2015 JabRef contributors.
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/
package net.sf.jabref.model.entry;

import net.sf.jabref.model.database.BibtexDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used to represent an unknown entry type, e.g. encountered
 * during bibtex parsing. The only known information is the type name.
 * This is useful if the bibtex file contains type definitions that are used
 * in the file - because the entries will be parsed before the type definitions
 * are found. In the meantime, the entries will be assigned an
 * UnknownEntryType giving the name.
 */
public class UnknownEntryType implements EntryType {
    private final String name;
    private final List<String> requiredFields;
    private final List<String> optionalFields;

    public UnknownEntryType(String name) {
        this.name = name;

        requiredFields = new ArrayList<>();
        optionalFields = new ArrayList<>();

        // key is always required
        requiredFields.add("bibtexkey");
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public boolean hasAllRequiredFields(BibtexEntry entry, BibtexDatabase database) {
        return true;
    }

    @Override
    public EntryTypes getEntryType() {
        return EntryTypes.UNKNOWN;
    }

    @Override
    public boolean isVisibleAtNewEntryDialog() {
        return true;
    }

    @Override
    public List<String> getOptionalFields() {
        return Collections.unmodifiableList(optionalFields);
    }

    @Override
    public List<String> getRequiredFields() {
        return Collections.unmodifiableList(requiredFields);
    }

    public List<String> getPrimaryOptionalFields() {
        return getOptionalFields();
    }

    public List<String> getSecondaryOptionalFields() {
        return Collections.unmodifiableList(new ArrayList<>(0));
    }

    @Override
    public int compareTo(EntryType o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public List<String> getRequiredFieldsForCustomization() {
        return getRequiredFields();
    }

    @Override
    public boolean isRequired(String field) {
        List<String> requiredFields = getRequiredFields();

        if (requiredFields == null) {
            return false;
        }
        return requiredFields.contains(field);
    }

    @Override
    public boolean isOptional(String field) {
        List<String> optionalFields = getOptionalFields();

        if (optionalFields == null) {
            return false;
        }
        return optionalFields.contains(field);
    }
}
