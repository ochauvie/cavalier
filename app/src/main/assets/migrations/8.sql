CREATE TABLE planningEvent (Id INTEGER PRIMARY KEY AUTOINCREMENT,moniteur INTEGER REFERENCES personne(Id) ON DELETE NO ACTION ON UPDATE NO ACTION,cavalier INTEGER REFERENCES personne(Id) ON DELETE NO ACTION ON UPDATE NO ACTION,monture INTEGER REFERENCES cheval(Id) ON DELETE NO ACTION ON UPDATE NO ACTION,type TEXT,dateDebut INTEGER,dateFin INTEGER,observation TEXT)
