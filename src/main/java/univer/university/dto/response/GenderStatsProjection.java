package univer.university.dto.response;

public interface GenderStatsProjection {
    Long getTotal();
    Long getMaleCount();
    Long getFemaleCount();
    Double getMalePercentage();
    Double getFemalePercentage();
}
