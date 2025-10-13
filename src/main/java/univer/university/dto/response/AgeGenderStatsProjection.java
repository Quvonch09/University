package univer.university.dto.response;

public interface AgeGenderStatsProjection {
    String getAgeGroup();
    Long getMaleCount();
    Long getFemaleCount();
    Long getTotal();
    Double getPercentage();
}
