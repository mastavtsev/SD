package com.kpo.springshaurma.model;


import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
//@Table("SHAURMA_ORDER")
@Entity
public class ShaurmaOrder {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Shaurma> shaurmaList = new ArrayList<>();

    public void addShaurma(Shaurma shaurma) {
        this.shaurmaList.add(shaurma);
    }
}
