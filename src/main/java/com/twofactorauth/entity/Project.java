package com.twofactorauth.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NamedQueries({
    @NamedQuery(name="Project.findAllProjects",
        query = "select p from Project p order by p.name"),
    @NamedQuery(name="Project.findProjectById",
        query = "select p from Project p where p.id = :id"),
    @NamedQuery(name="Project.findTaskById",
        query = "select t from Task t where t.id = :id "),
    @NamedQuery(name="Project.findTasksByProjectId",
        query = "select t from Project p, Task t " +
                "where p.id = :id and t.project = p"),
})
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="PROJECT_ID") private Long id;

    @NotEmpty
    @Size(max=64) private String name;
    private String headline;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project",
        fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();

    public Project() { /* Required for JPA */
        this(null,null);
    }
    public Project(String name) {
        this(name, null );
    }
    public Project(String name, String headline) {
        this(name,headline,null);
    }
    public Project(String name, String headline, String description ) {
        this.name = name;
        this.headline = headline;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getHeadline() { return headline; }
    public void setHeadline(String headline) { this.headline = headline; }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }

    public boolean addTask( Task task ) {
        if ( ! tasks.contains( task) ) {
            Project oldProject = task.getProject();
            if ( oldProject != null ) {
                removeTask( task );
            }
            tasks.add(task);
            task.setProject(this);
            return true;
        } else { return false; }
    }

    public boolean removeTask( Task task ) {
        if ( tasks.contains( task) ) {
            tasks.remove(task);
            task.setProject(null);
            return true;
        } else { return false; }
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", headline='" + headline + '\'' +
                ", description='" + description + '\'' +
                ", tasks=" + tasks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        if (!Objects.equals(description, project.description)) return false;
        if (!Objects.equals(id, project.id)) return false;
        return Objects.equals(name, project.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}