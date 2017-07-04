package teamthree.twodo.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import teamthree.twodo.commons.exceptions.IllegalValueException;
import teamthree.twodo.model.tag.Tag;
import teamthree.twodo.model.task.Address;
import teamthree.twodo.model.task.Deadline;
import teamthree.twodo.model.task.Email;
import teamthree.twodo.model.task.Name;
import teamthree.twodo.model.task.ReadOnlyTask;
import teamthree.twodo.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedPerson {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyTask source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Deadline deadline = new Deadline(this.phone);
        final Email email = new Email(this.email);
        final Address address = new Address(this.address);
        final Set<Tag> tags = new HashSet<>(personTags);
        return new Task(name, deadline, email, address, tags);
    }
}