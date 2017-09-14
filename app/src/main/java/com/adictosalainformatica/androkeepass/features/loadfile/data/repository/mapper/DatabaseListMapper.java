/*
 *      DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                  Version 2, December 2004
 *
 *      Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
 *
 *      Everyone is permitted to copy and distribute verbatim or modified
 *      copies of this license document, and changing it is allowed as long
 *      as the name is changed.
 *
 *      DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *      TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *      0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package com.adictosalainformatica.androkeepass.features.loadfile.data.repository.mapper;

import com.adictosalainformatica.androkeepass.features.loadfile.domain.model.Database;

import java.io.File;

/**
 * DatabaseListMapper
 *
 * <p>Mapper class to create database list</p>
 */
public class DatabaseListMapper {

    private DatabaseListMapper(){

    }

    public static Database fromFileToDtabaseModel(File file){

        return new Database (file.getName(),file.getAbsolutePath());
    }
}
