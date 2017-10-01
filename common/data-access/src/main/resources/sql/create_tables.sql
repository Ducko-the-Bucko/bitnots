
create table formula (
    formula_id bigserial not null primary key,
--    language_id bigint 
    formula text not null unique
);

create table formula_role (
    role text primary key
);

insert formula_role (role) values
    ('axiom'),
    ('hypothesis'),
    ('definition'),
    ('assumption'),
    ('lemma'),
    ('theorem'),
    ('corollary'),
    ('conjecture'),
    ('negated_conjecture'),
    ('plain'),
    ('type'),
    ('fi_domain'),
    ('fi_functors'),
    ('fi_predicates'),
    ('unknown');

create table formula_plus (
    formula_plus_id bigserial not null primary key,
    role text not null references formula_role,
    formula_id not null references formula,
    name text not null unique
);
create index on formula_plus (role);

create table axiom_set (
    axiom_set_id bigserial not null primary key,
    name text not null unique

);

create table axiom_axiom_set (
    axiom_set_id bigint not null references axiom_set,
    formula_plus_id bigint not null references formula_plus,
    primary key (axiom_set_id, axiom_id)
);

create trigger ensure_axiom_in_axiom_set 
    before insert or update
    on axiom_axiom_set
    for each row
    execute procedure check_axiom_in_axiom_set();

create table problem (
    problem_id bigserial not null primary key,
    name text not null unique,

);

create table problem_axiom_set (
    axiom_set_id bigint not null references axiom_set,
    problem_id bigint not null references problem,
    primary key (axiom_set_id, problem_id)

);

create table problem_formula_plus (
    formula_plus_id bigint not null references formula_plus,
    problem_id bigint not null references problem,
    primary key (formula_plus_id, problem_id)

);