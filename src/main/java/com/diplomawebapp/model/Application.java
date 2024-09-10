package com.diplomawebapp.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="applications")
public class Application {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    private Student student;
    
    @ManyToOne
    private Subject subject;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Column(name="implementation_grade")
    private Double implementationgrade;
    @Column(name="report_grade")
    private Double reportgrade;
    @Column(name="presentation_grade")
    private Double presentationgrade;
    @Column(name="final_grade")
    private Double finalgrade;

    public Application() {
    	
    }

    public Application(Student student, Subject subject, Status status) {
        this.student = student;
        this.subject = subject;
        this.status = status;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

	public Double getImplementationgrade() {
		return implementationgrade;
	}

	public void setImplementationgrade(Double implementationgrade) {
		this.implementationgrade = implementationgrade;
	}

	public Double getReportgrade() {
		return reportgrade;
	}

	public void setReportgrade(Double reportgrade) {
		this.reportgrade = reportgrade;
	}

	public Double getPresentationgrade() {
		return presentationgrade;
	}

	public void setPresentationgrade(Double presentationgrade) {
		this.presentationgrade = presentationgrade;
	}

	public Double getFinalgrade() {
		return finalgrade;
	}

	public void setFinalgrade(Double finalgrade) {
		this.finalgrade = finalgrade;
	}
    
}
