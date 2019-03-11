package org.projectbarbel.playground.barbel;

import java.time.LocalDate;

import org.projectbarbel.histo.BarbelHisto;
import org.projectbarbel.histo.BarbelHistoBuilder;
import org.projectbarbel.histo.BarbelQueries;
import org.projectbarbel.histo.DocumentId;

public class BarbelSome {

    public static void main(String[] args) {
        BarbelHisto<SomePojo> barbel = BarbelHistoBuilder.barbel().build();
        barbel.save(new SomePojo("test"), LocalDate.now(), LocalDate.MAX);
        barbel.retrieve(BarbelQueries.all());
        System.out.println(barbel.prettyPrintJournal("test"));
    }
    
    public static class SomePojo{
        @DocumentId
        private String docId;

        public SomePojo(String docId) {
            super();
            this.docId = docId;
        }
    }
}
