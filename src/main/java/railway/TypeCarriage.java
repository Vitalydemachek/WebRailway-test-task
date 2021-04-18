package railway;


import java.util.Objects;

/**
 * @author user
 */
public class TypeCarriage {
	private final String type;
	private Long id;

	public TypeCarriage(Long id, String type) {
		this.id = id;
		this.type = type;

	}

	public String getType() {
		return type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TypeCarriage that = (TypeCarriage) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(type, that.type);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, type);
	}
}
