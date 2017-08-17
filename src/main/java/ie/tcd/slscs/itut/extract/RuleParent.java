class IntegerRule extends Rule {
    private final int child1Property;

    public int getChild1Property() {
        return child1Property;
    }

    public static abstract class Builder<T extends Child1> extends RuleParent.Builder<T> {
        public abstract T build();
    }

    public static Builder<?> builder() {
        return new Builder<Child1>() {
            @Override
            public Child1 build() {
                return new Child1(this);
            }
        };
    }

    protected Child1(Builder<?> builder) {
        super(builder);
        this.child1Property = builder.child1Property;
    }

}
