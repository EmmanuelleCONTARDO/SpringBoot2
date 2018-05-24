package co.simplon.crud.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Named;

import co.simplon.crud.model.Foo;

@Named
public interface FooService {
	public Foo saveFoo(Foo foo);
	public List<Foo> getAll();
	public Optional<Foo> findbyId(Long id);
// TODO ajouter les autres méthodes
}
