package co.edu.poligran.server.dtos;

import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse<T> {
    private ArrayList<T> result;
}
