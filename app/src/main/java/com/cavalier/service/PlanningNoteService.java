package com.cavalier.service;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.cavalier.model.PlanningNote;

import java.util.List;

public class PlanningNoteService {

    public static List<PlanningNote> getAll() {
        return new Select()
                .from(PlanningNote.class)
                .orderBy("dateDebut DESC")
                .execute();
    }

    public static void deleteAll() {
        new Delete().from(PlanningNote.class)
                .execute();
    }

    public static PlanningNote getById(Long id) {
        return new Select()
                .from(PlanningNote.class)
                .where("id = ?", id)
                .executeSingle();
    }


}
